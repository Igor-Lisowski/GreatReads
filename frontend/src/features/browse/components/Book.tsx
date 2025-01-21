import { TableCell, TableRow } from "@mui/material";
import React from "react";

interface BookProps {
  book: {
    id: number;
    name: string;
    rating: number;
    author: string;
    ratingsNumber: number;
  };
}

const Book: React.FC<BookProps> = ({ book }) => {
  return (
    <TableRow key={book.id}>
      <TableCell>{book.name}</TableCell>
      <TableCell>{book.rating}</TableCell>
      <TableCell>{book.author}</TableCell>
      <TableCell>{book.ratingsNumber}</TableCell>
    </TableRow>
  );
};

export default Book;
