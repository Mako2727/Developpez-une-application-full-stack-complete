import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { ArticleService } from '../../../services/article.service';
import { ThemeService } from '../../../services/theme.service';
import { NewArticle } from '../../interfaces/newarticle.interface';

interface Theme {
  id: number;
  name: string;
}

@Component({
  selector: 'app-article-modal',
  templateUrl: './article-modal.component.html',
  styleUrls: ['./article-modal.component.scss']
})
export class ArticleModalComponent implements OnInit {
  // Article avec themeId inclus
  article = {
    title: '',
    content: '',
    themeId: null
  };

  // Liste des thèmes
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
    topicId: this.article.themeId, // renommer ici
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