import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { ArticleService } from '../../../core/services/article.service';
import { ThemeService } from '../../../core/services/theme.service';
import { NewArticle } from '../../../shared/models/newarticle.interface';

interface Theme {
  id: number;
  name: string;
}

@Component({
  selector: 'app-article-modal',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule
  ],
  templateUrl: './article-modal.component.html',
  styleUrls: ['./article-modal.component.scss']
})
export class ArticleModalComponent implements OnInit {
  article = {
    title: '',
    content: '',
    themeId: null
  };

  themes: Theme[] = [];

  constructor(
    private dialogRef: MatDialogRef<ArticleModalComponent>,
    private themeService: ThemeService,
    private articleService: ArticleService
  ) {}

  ngOnInit(): void {
    this.loadThemes();
  }

  loadThemes(): void {
    this.themeService.getThemes().subscribe({
      next: (data: Theme[]) => {
        this.themes = data;
        console.log('Thèmes reçus :', this.themes);
      },
      error: err => console.error('Erreur chargement thèmes', err)
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onSubmit(): void {
    if (!this.article.themeId) return;

    const dto: NewArticle = {
      topicId: this.article.themeId,
      title: this.article.title,
      content: this.article.content
    };

    this.articleService.createArticle(dto).subscribe({
      next: (created) => {
        console.log('Article créé', created);
        this.dialogRef.close(created);
      },
      error: (err) => console.error('Erreur création article', err)
    });
  }
}
