import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './features/auth/components/register/register.component'; 
import { DashboardComponent } from './pages/dashboard/dashboard.component'; 
import { AuthGuard } from './features/auth/components/guards/auth.guards'; 
import { MeComponent } from './features/auth/components/me/me.component'; 


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent , canActivate: [AuthGuard] },
  { path: 'me', component: MeComponent , canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
