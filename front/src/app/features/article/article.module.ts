import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticleComponent } from './article.component';
import { ArticleRoutingModule } from './article-routing.module';

// ⚡ Import nécessaire pour mat-card
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [ArticleComponent],
  imports: [
    CommonModule,
    ArticleRoutingModule,
    MatCardModule  
  ]
})
export class ArticleModule {}