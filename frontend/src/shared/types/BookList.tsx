import { FetchState } from "./FetchState";

export interface BookList {
  id: number;
  goodReadsId: number;
  name: string;
  booksNumber: number;
  votersNumber: number;
  fetchState: FetchState;
}
