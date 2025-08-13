import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ArticleModalComponent } from '../modal/article-modal/article-modal.component';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements AfterViewInit {
  @ViewChild('createArticleBtnEl') createArticleBtn!: ElementRef<HTMLButtonElement>;

  constructor(private dialog: MatDialog) {}

  ngAfterViewInit(): void {
    // Le ViewChild est maintenant initialisé
  }

  openCreateArticleDialog(): void {
    // retire le focus seulement si l'élément existe
    this.createArticleBtn?.nativeElement?.blur();

    const dialogRef = this.dialog.open(ArticleModalComponent, { width: '500px' });

    dialogRef.afterClosed().subscribe(result => {
      if (result) console.log('Article créé :', result);
    });
  }
}