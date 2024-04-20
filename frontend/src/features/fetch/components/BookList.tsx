import {
  Box,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { useGetBookListsByGenreIdQuery } from "../../../shared/api/bookListApi";
import { FetchState } from "../../../shared/types/FetchState";

interface BookListProps {
  genreId: number;
}

function BookList({ genreId }: BookListProps) {
  const { data, error, isLoading } = useGetBookListsByGenreIdQuery(genreId);
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
                <TableCell>Fetch State</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {data.map((bookList) => (
                <TableRow>
                  <TableCell>{bookList.name}</TableCell>
                  <TableCell>{bookList.booksNumber}</TableCell>
                  <TableCell>{bookList.votersNumber}</TableCell>
                  <TableCell>
                    {bookList.fetchState === FetchState.NOT_FETCHED
                      ? "Not Fetched"
                      : bookList.fetchState === FetchState.FETCHED
                      ? "Fetched"
                      : "Fetching"}
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
