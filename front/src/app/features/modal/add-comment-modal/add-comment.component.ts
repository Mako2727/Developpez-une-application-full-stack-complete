import { Component,Inject  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators, FormsModule } from '@angular/forms';
import { MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommentService } from '../../../core/services/comment.service';
import { createComment } from '../../../shared/models/createComment.interface';

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    MatFormFieldModule,
     ReactiveFormsModule,
    MatInputModule,
    MatButtonModule
  ]
})
export class AddCommentComponent {
  commentForm = this.fb.group({
    content: ['', [Validators.required, Validators.minLength(3)]]
  });
content: string = ''; 
  constructor(
    private fb: FormBuilder,
    private commentService: CommentService,
    public dialogRef: MatDialogRef<AddCommentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { articleId: number }
  ) {}

  submitComment(): void {
    console.log('avant validation:',  );
        if (this.commentForm.invalid) {
      console.log('Formulaire invalide', this.commentForm.value);
      return;
    }
   console.log('apres validation:',  );
    const newComment: createComment = { content: this.commentForm.value.content ?? '' };
     console.log('commentaire:', this.content );
    this.commentService.addComment(this.data.articleId, newComment).subscribe({
      next: (res) => this.dialogRef.close(res),
      error: (err) => console.error('Erreur création commentaire', err)
    });
  }

  cancel(): void {
    this.dialogRef.close();
  }
}