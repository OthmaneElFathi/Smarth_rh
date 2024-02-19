export interface PaginatedCandidateResponse {
    content: CandidateResponse[]; 
    pageable: any; 
    last: boolean; 
    totalElements: number; 
    totalPages: number; 
    size?: number; 
    number?: number; 
    sort?: any; 
    numberOfElements?: number; 
    first?: boolean; 
    empty?: boolean; 
  }
  export interface CandidateResponse{
    id: string;
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    role: string;
    description: string;
    profilePictureUrl:string;
    skills: string[];
  }