import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { postDetail } from '../../shared/models/postDetail.interface';
import { ArticleService } from '../../core/services/article.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { CommentService } from '../../core/services/comment.service';


@Component({
  selector: 'app-article-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatListModule,
    MatDialogModule,
    MatIconModule,
    FormsModule
  ],
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss']
})
export class ArticleDetailComponent implements OnInit {
  article!: postDetail;
  newComment: string = '';
  constructor(
    private commentService: CommentService,
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private dialog: MatDialog,
    private location: Location,
    private router: Router
  ) {}

  

  ngOnInit(): void {
   console.log('ici.... :');
   const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log('ArticleDetail ngOnInit', id)
    this.loadArticle(id);
  }

  loadArticle(id: number): void {
    this.articleService.getArticleById(id).subscribe({
      next: (data: postDetail) => this.article = data,
      error: (err) => console.error('Erreur chargement article', err)
    });
  }

  goBack(): void {
   this.router.navigate(['/article']);
}
 sendComment(): void {
     console.log('avant validation:', this.newComment);

  if (!this.newComment || !this.newComment.trim()) {
    console.log('Formulaire invalide, contenu vide:', this.newComment);
    return;
  }

  console.log('après validation:', this.newComment);

  const newComment = { content: this.newComment.trim() };

  // appel du service (à remplacer par commentService si tu en as un)
  this.commentService.addComment(this.article.id, newComment).subscribe({
    next: (res) => {
      console.log('Commentaire créé:', res);
      this.newComment = ''; // reset champ
      this.loadArticle(this.article.id); // recharger l’article pour voir le nouveau commentaire
    },
    error: (err) => console.error('Erreur création commentaire', err)
  });
  }


}