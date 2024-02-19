import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ResumeData } from '../types/resume.interface';
import { AuthService } from './auth.service';
import { BACKEND_URL } from '../utils/backend.url';

@Injectable({
  providedIn: 'root'
})
export class ResumeService {
  constructor(private http: HttpClient,private authService:AuthService) { }

  uploadResume(resumeData: ResumeData) {
    const formData = new FormData();
    formData.append('title', resumeData.title);
    formData.append('resume', resumeData.resume);
    console.log(formData)
    return this.http.post<any>(`${BACKEND_URL}/candidates/upload-resume/${this.authService.getUsername()}`, formData);
  }
  downloadResume() {
    return this.http.get(`${BACKEND_URL}/candidates/download/${this.authService.getUsername()}`, { responseType: 'blob' });
  }
  downloadResumeCandidate(username: string) {
    return this.http.get(`${BACKEND_URL}/candidates/download/${username}`, { responseType: 'blob' });
  }
}
