import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { ThemeService } from '../../core/services/theme.service';
import { Theme } from '../../shared/models/theme.interface';

@Component({
  selector: 'app-theme',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatDialogModule,
    MatButtonModule
  ],
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss']
})
export class ThemeComponent implements OnInit, AfterViewInit {
  @ViewChild('createThemeBtnEl') createThemeBtn!: ElementRef<HTMLButtonElement>;
  themes: Theme[] = [];

  constructor(private dialog: MatDialog, private themeService: ThemeService) {}

  ngOnInit(): void {
    this.loadThemes();
  }

  ngAfterViewInit(): void {}

  loadThemes(): void {
    this.themeService.getAllThemes().subscribe({
      next: (data) => this.themes = data,
      error: (err) => console.error('Erreur chargement thèmes', err)
    });
  }

toggleSubscription(theme: Theme): void {
  const currentlySubscribed = theme.subscribed;
  

  theme.subscribed = !currentlySubscribed;

  if (!currentlySubscribed) {

    this.themeService.subscribe(theme.id).subscribe({
      next: () => console.log(`Abonné au thème "${theme.id}"`),
      error: (err) => {
        console.error('Erreur abonnement', err);
        theme.subscribed = currentlySubscribed; 
      }
    });
  } else {
    
    this.themeService.unsubscribe(theme.id).subscribe({
      next: () => console.log(`Désabonné du thème "${theme.id}"`),
      error: (err) => {
        console.error('Erreur désabonnement', err);
        theme.subscribed = currentlySubscribed; 
      }
    });
  }
}
}