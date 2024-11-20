import { JobStatus } from "shared/types/JobStatus";

export interface Job {
  id: number;
  status: JobStatus;
}
