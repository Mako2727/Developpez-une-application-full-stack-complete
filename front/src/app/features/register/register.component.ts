import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { SessionService } from 'src/app/core/services/session.service';
import { AuthService } from '../../core/services/auth.service';
import { AuthSuccess } from '../../shared/models/authSuccess.interface';
import { User } from 'src/app/shared/models/user.interface';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  public onError = false;

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private sessionService: SessionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(3)]]
    });
  }

onSubmit(): void {
  if (!this.registerForm.valid) return;

  const registerRequest = this.registerForm.value;
  this.authService.register(registerRequest).subscribe({
    next: (response: AuthSuccess) => {
  
      localStorage.setItem('token', response.token);

     
      this.authService.me().subscribe((user: User) => {
        
        const sessionInfo = {
          token: response.token,
          username: user.username,
          email: user.email
        };

        this.sessionService.logIn(sessionInfo);
        console.log("Token et session enregistrés", sessionInfo);
        this.router.navigate(['/dashboard']);
      });
    },
    error: () => this.onError = true
  });
}

  goBack(): void {
    this.router.navigate(['/']);
  }
}