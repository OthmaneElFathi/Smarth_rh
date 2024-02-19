import { Recruiter } from "./recruiter.interface";

export interface JobOfferRequest {
    name: string;
    description: string;
    daysUntilExpired: number;
    requiredSkills: string[];
  }

  export interface JobOfferResponse {
    id: string;
    name: string;
    description: string;
    dateAdded: Date;
    dateExpired: Date;
    requiredSkills: string[];
    recruiter: Recruiter;
  }
  export interface PaginatedJobOfferResponse {
    content: JobOfferResponse[]; 
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