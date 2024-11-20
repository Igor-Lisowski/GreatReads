import { Job } from "shared/types/Job";

export interface BookList {
  id: number;
  goodReadsId: number;
  name: string;
  booksNumber: number;
  votersNumber: number;
  job: Job;
}
