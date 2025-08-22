import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { LoginComponent } from './features/login/login.component';
import { RegisterComponent } from './features/register/register.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { MeComponent } from './features/me/me.component';
import { ArticleComponent } from './features/article/article.component';
import { ThemeComponent } from './features/theme/theme.component';
import { AuthGuard } from './core/guards/auth.guards';
import { ArticleDetailComponent  } from './features/article-detail/article-detail.component';

export const routes: Routes = [
  { path: '', component: HomeComponent, title: 'Home' },
  { path: 'login', component: LoginComponent, title: 'Login' },
  { path: 'register', component: RegisterComponent, title: 'Register' },
  { path: 'me', component: MeComponent, canActivate: [AuthGuard], title: 'Me' },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard], title: 'Dashboard' },
  { path: 'article', component: ArticleComponent, canActivate: [AuthGuard], title: 'Article' },
  { path: 'theme', component: ThemeComponent, canActivate: [AuthGuard], title: 'Theme' },
  { path: 'articleDetail/:id', component: ArticleDetailComponent,  canActivate: [AuthGuard],  title: 'Article Detail'  },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];