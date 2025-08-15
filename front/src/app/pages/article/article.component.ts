import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ArticleModalComponent } from '../modal/article-modal/article-modal.component';
import { ArticleService } from '../../services/article.service';
import { postDetail } from '../interfaces/postDetail.interface'; 
import { ViewDetailComponent } from '../modal/view-detail/view-detail.component';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit, AfterViewInit {
  @ViewChild('createArticleBtnEl') createArticleBtn!: ElementRef<HTMLButtonElement>;
sortAsc: boolean = false; 
    articles: postDetail[] = [];
  constructor(private dialog: MatDialog,
    private articleService: ArticleService) {}

      ngOnInit(): void {
    this.loadArticles();
  }

  ngAfterViewInit(): void {
   
  }

  openCreateArticleDialog(): void {
    // retire le focus seulement si l'élément existe
    this.createArticleBtn?.nativeElement?.blur();

    const dialogRef = this.dialog.open(ArticleModalComponent, { width: '500px' });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
      // recharge la liste des articles après la création
      this.loadArticles();
    }
    });
  }

   loadArticles(): void {
  this.articleService.getAllArticles().subscribe({
    next: (data) => {
      console.log('Articles reçus :', data); // <-- log de la data
      this.articles = data;
    },
    error: (err) => console.error('Erreur chargement articles', err)
  });
}

openArticleDetail(article: postDetail): void {
  this.dialog.open(ViewDetailComponent, {
    width: '600px',
    data: { id: article.id } // <-- seulement l'id
  });
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