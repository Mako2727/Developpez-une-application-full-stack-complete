import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { postDetail } from '../../interfaces/postDetail.interface';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from '../../../services/article.service';

@Component({
  selector: 'app-view-detail',
  templateUrl: './view-detail.component.html',
  styleUrls: ['./view-detail.component.scss']
})
export class ViewDetailComponent {
  article!: postDetail; 

constructor(
  private articleService: ArticleService,
  public dialogRef: MatDialogRef<ViewDetailComponent>,
  @Inject(MAT_DIALOG_DATA) public data: { id: number }
) {}

ngOnInit(): void {
  const id = this.data.id;
  this.articleService.getArticleById(id).subscribe({
    next: (data: postDetail) => this.article = data,
    error: (err) => console.error('Erreur chargement article', err)
  });
}

  close(): void {
    this.dialogRef.close();
  }
}
