export interface LoginResponse {
    username: string;
    jwtToken: string;
    roles: string[];
    profilePicture: string; 
    response: string;
  }
  