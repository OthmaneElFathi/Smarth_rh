import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { LoginData } from '../types/login.interface';
import { RegisterData } from '../types/regsiter.interface';
import { LoginResponse } from '../types/loginResponse.interface';
import { CandidateService } from './candidate.service';
import { Observable } from 'rxjs';
import { BACKEND_URL } from '../utils/backend.url';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient, private router: Router, private candidateService: CandidateService) { }

  registerUser(registerData: RegisterData) {
    return this.http.post<any>(`${BACKEND_URL}/auth/register`, registerData);
  }

  loginUser(loginData: LoginData) {
    return this.http.post<LoginResponse>(`${BACKEND_URL}/auth/login`, loginData).pipe(
      tap(response => {
        if (response.jwtToken) {
          localStorage.setItem('token', response.jwtToken);
          localStorage.setItem('role',response.roles[0]);
          localStorage.setItem('username',response.username);
        }

        if (response.roles.includes('ROLE_CANDIDATE')) {
          this.candidateService.checkResumeUploaded(response.username).subscribe(hasResume => {
            if (!hasResume) {
              this.router.navigate(['/upload-resume']);
            } else {
              localStorage.setItem('hasResume',JSON.stringify(hasResume));
              this.router.navigate(['/job-offers']);
            }
          })
        } else if (response.roles.includes('ROLE_RECRUITER')) {
          this.router.navigate(['/my-job-offers']);
        
      }
      })
    );
  }

  uploadProfilePicture(profilePicture: File): Observable<any> {
    const formData = new FormData();
    formData.append('profilePicture', profilePicture);
    return this.http.put<any>(`${BACKEND_URL}/users/upload-profile-picture`, formData);
  }
  logoutUser() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
  getRole(): string | null {
    return localStorage.getItem('role');
  }
  getUsername(): string | null {
    return localStorage.getItem('username');
  }
  isLoggedIn(): boolean {
    return this.getToken() !== null;
  }
  hasResume(): boolean {
    return localStorage.getItem('hasResume') !== null;
  }
}
