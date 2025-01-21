import { Button, TableCell, TableRow } from "@mui/material";
import React, { useState } from "react";
import { useScrapeBooksByBookListIdMutation } from "shared/api/bookApi";
import { JobStatus } from "shared/types/JobStatus";

interface BookListProps {
  bookList: {
    id: number;
    name: string;
    booksNumber: number;
    votersNumber: number;
    job: {
      status: string;
    };
  };
}

const BookList: React.FC<BookListProps> = ({ bookList }) => {
  const [jobStatus, setJobStatus] = useState(bookList?.job?.status);
  const [scrapeBooksByBookListId] = useScrapeBooksByBookListIdMutation();

  const handleFetchClick = (bookListId: number) => {
    scrapeBooksByBookListId(bookListId);
    setJobStatus(JobStatus.PENDING);
  };

  return (
    <TableRow key={bookList.id}>
      <TableCell>{bookList.name}</TableCell>
      <TableCell>{bookList.booksNumber}</TableCell>
      <TableCell>{bookList.votersNumber}</TableCell>
      <TableCell>
        <Button
          onClick={() => handleFetchClick(bookList.id)}
          variant="outlined"
          disabled={
            jobStatus === JobStatus.PENDING || jobStatus === JobStatus.COMPLETED
          }
        >
          {!jobStatus || jobStatus === JobStatus.FAILED
            ? "Fetch"
            : jobStatus === JobStatus.COMPLETED
            ? "Fetched"
            : "Fetching"}
        </Button>
      </TableCell>
    </TableRow>
  );
};

export default BookList;
