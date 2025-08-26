import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { ArticleService } from '../../core/services/article.service';
import { postDetail } from '../../shared/models/postDetail.interface';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router'; 
import { ArticleDetailComponent } from '../article-detail/article-detail.component';

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatCardModule,
     RouterModule,
     ArticleDetailComponent
  
  ],
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit, AfterViewInit {
  @ViewChild('createArticleBtnEl') createArticleBtn!: ElementRef<HTMLButtonElement>;
  sortAsc: boolean = false;
  articles: postDetail[] = [];

  constructor(private dialog: MatDialog, private articleService: ArticleService, private router: Router) {}

  ngOnInit(): void {
    this.loadArticles();
  }

  ngAfterViewInit(): void {}

  CreateArticle(): void {
   
this.router.navigate(['/createarticle']);
  }

  loadArticles(): void {
    this.articleService.getAllArticles().subscribe({
      next: (data) => this.articles = data,
      error: (err) => console.error('Erreur chargement articles', err)
    });
  }

openArticleDetail(article: postDetail): void {
this.router.navigate(['/articleDetail', article.id]);
}

  toggleSortOrder(): void {
    this.sortAsc = !this.sortAsc;
    this.articles.sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      return this.sortAsc ? dateA - dateB : dateB - dateA;
    });
  }

  
}