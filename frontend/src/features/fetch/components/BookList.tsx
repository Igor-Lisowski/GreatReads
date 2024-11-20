import {
  Box,
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { useScrapeBooksByBookListIdMutation } from "shared/api/bookApi";
import { useGetBookListsByGenreIdQuery } from "shared/api/bookListApi";
import { JobStatus } from "shared/types/JobStatus";

interface BookListProps {
  genreId: number;
}

function BookList({ genreId }: BookListProps) {
  const { data, error, isLoading } = useGetBookListsByGenreIdQuery(genreId);
  const [scrapeBooksByBookListId] = useScrapeBooksByBookListIdMutation();

  const handleFetchClick = (bookListId: number) => {
    scrapeBooksByBookListId(bookListId);
  };

  return (
    <Box>
      {" "}
      {error ? (
        <>Oh no, there was an error</>
      ) : isLoading ? (
        <>Loading...</>
      ) : data ? (
        <TableContainer style={{ maxHeight: "75vh" }}>
          <Table stickyHeader>
            <TableHead>
              <TableRow>
                <TableCell>Name</TableCell>
                <TableCell>Books Number</TableCell>
                <TableCell>Voters Number</TableCell>
                <TableCell></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {data.map((bookList) => (
                <TableRow key={bookList.id}>
                  <TableCell>{bookList.name}</TableCell>
                  <TableCell>{bookList.booksNumber}</TableCell>
                  <TableCell>{bookList.votersNumber}</TableCell>
                  <TableCell>
                    <Button
                      onClick={() => handleFetchClick(bookList.id)}
                      variant="outlined"
                      disabled={bookList.job?.status === JobStatus.COMPLETED}
                    >
                      {bookList.job === null ||
                      bookList.job?.status === JobStatus.FAILED
                        ? "Fetch"
                        : bookList.job?.status === JobStatus.COMPLETED
                        ? "Fetched"
                        : "Fetching"}
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : null}
    </Box>
  );
}

export default BookList;
