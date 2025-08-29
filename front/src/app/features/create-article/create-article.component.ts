import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { Router } from '@angular/router';
import { ArticleService } from '../../core/services/article.service';
import { ThemeService } from '../../core/services/theme.service';
import { NewArticle } from '../../shared/models/newarticle.interface';
import { MatIconModule } from '@angular/material/icon'; 

interface Theme {
  id: number;
  name: string;
}

@Component({
  selector: 'app-create-article',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
     MatIconModule
  ],
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent implements OnInit {
  article = {
    title: '',
    content: '',
    themeId: null
  };

  themes: Theme[] = [];

  constructor(
    private themeService: ThemeService,
    private articleService: ArticleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadThemes();
  }

  loadThemes(): void {
    this.themeService.getSubscribedTheme().subscribe({
      next: (data: Theme[]) => {
        this.themes = data;
      },
      error: err => console.error('Erreur chargement thèmes', err)
    });
  }

  onCancel(): void {
    
    this.router.navigate(['/article']);
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
       
        this.router.navigate(['/article']);
      },
      error: (err) => console.error('Erreur création article', err)
    });
  }
}