import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { postDetail } from '../../../shared/models/postDetail.interface';
import { ArticleService } from '../../../core/services/article.service';
import { AddCommentComponent } from '../add-comment-modal/add-comment.component';

@Component({
  selector: 'app-view-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatListModule
  ],
  templateUrl: './view-detail.component.html',
  styleUrls: ['./view-detail.component.scss']
})
export class ViewDetailComponent {
  article!: postDetail;

  constructor(
    private articleService: ArticleService,
    public dialogRef: MatDialogRef<ViewDetailComponent>,
    private dialog: MatDialog, // <-- Injection manquante
    @Inject(MAT_DIALOG_DATA) public data: { id: number }
  ) {}

  ngOnInit(): void {
    const id = this.data.id;
    this.loadArticle(id);
  }

  loadArticle(id: number): void {
    this.articleService.getArticleById(id).subscribe({
      next: (data: postDetail) => this.article = data,
      error: (err) => console.error('Erreur chargement article', err)
    });
  }

  close(): void {
    this.dialogRef.close();
  }

  openCreateCommentDialog(): void {
    const dialogRef = this.dialog.open(AddCommentComponent, {
      width: '500px',
      data: { articleId: this.article.id }
    });

dialogRef.afterClosed().subscribe((result: any) => {
  if (result) {
    this.loadArticle(this.article.id);
  }
});
  }
}