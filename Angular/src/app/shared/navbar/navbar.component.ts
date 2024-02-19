import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  constructor(private authService: AuthService) {}

  logout(): void {
    this.authService.logoutUser();
  }
  hasResume(): boolean {
    return this.authService.hasResume();
  }
  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }
  isCandidate(): boolean {
    return this.authService.getRole()=="ROLE_CANDIDATE";
  }

  isRecruiter(): boolean {
    return this.authService.getRole()=="ROLE_RECRUITER";
  }
}
