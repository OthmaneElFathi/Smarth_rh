import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { RegisterData } from '../../types/regsiter.interface';import { LoginData } from '../../types/login.interface';
;

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})


export class RegisterComponent {
  registerData: RegisterData = {
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    email: '',
    role: 'ROLE_CANDIDATE', 
    companyName: '',
    contactNumber: '',
    companyAddress: '',
    description: ''
  };
  profilePicture: File | null = null;

  constructor(private authService: AuthService) { }

  onRegisterSubmit(): void {
    this.authService.registerUser(this.registerData).subscribe(
      () => {
        
        const loginData: LoginData = {
          username: this.registerData.username,
          password: this.registerData.password
        };
        this.authService.loginUser(loginData).subscribe(
          () => {
            console.log('Login successful');

            if (this.profilePicture) {
              this.authService.uploadProfilePicture(this.profilePicture).subscribe(
                () => {
                  console.log('Profile picture uploaded successfully');
                },
                error => {
                  console.error('Error uploading profile picture:', error);
                }
              );
            }
          },
          error => {
            console.error('Login failed:', error);
          }
        );
      }
    );
  }

  onProfilePictureChange(event: any): void {
    const files: FileList = event.target.files;
    if (files.length > 0) {
      this.profilePicture = files.item(0);
      const fileNameElement = document.getElementById('fileName');
      if (fileNameElement) {
        fileNameElement.textContent = files.item(0)?.name || 'No file selected';
      }
    }
  }
}
