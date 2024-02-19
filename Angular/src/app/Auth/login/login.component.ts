import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { LoginData } from '../../types/login.interface';;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginData: LoginData = {
    username: '',
    password: ''
  };

  constructor(private authService: AuthService) { }

  onLoginSubmit() {
    this.authService.loginUser(this.loginData).subscribe(response => {
      console.log(response);
    });
  }
}
