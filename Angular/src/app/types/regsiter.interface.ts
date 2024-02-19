export interface RegisterData {
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    email: string;
    role: string;
    companyName?: string;
    contactNumber?: string;
    companyAddress?: string;
    description?: string;
    [key: string]: string | Blob | undefined;
  }