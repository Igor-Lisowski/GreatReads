import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { BookList } from "../types/BookList";
// Define a service using a base URL and expected endpoints
export const bookListApi = createApi({
  reducerPath: "bookListApi",
  baseQuery: fetchBaseQuery({
    baseUrl: "http://localhost:8080/",
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    },
  }),
  endpoints: (builder) => ({
    getBookListsByGenreId: builder.query<BookList[], number>({
      query: (genreId) => `book-list?genreId=${genreId}`,
    }),
  }),
});

// Export hooks for usage in functional components, which are
// auto-generated based on the defined endpoints
export const { useGetBookListsByGenreIdQuery } = bookListApi;
