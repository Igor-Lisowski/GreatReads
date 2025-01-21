import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { Book } from "shared/types/Book";
// Define a service using a base URL and expected endpoints
export const bookApi = createApi({
  reducerPath: "bookApi",
  baseQuery: fetchBaseQuery({
    baseUrl: "http://localhost:8080/",
    headers: {
      "Content-Type": "application/json",
      "Access-Control-Allow-Origin": "*",
    },
  }),
  endpoints: (builder) => ({
    scrapeBooksByBookListId: builder.mutation<void, number>({
      query: (bookListId) => ({
        url: `book/scrape?bookListId=${bookListId}`,
        method: "POST",
      }),
    }),
    getBooksByGenreId: builder.query<Book[], number>({
      query: (genreId) => `book?genreId=${genreId}`,
    }),
  }),
});

// Export hooks for usage in functional components, which are
// auto-generated based on the defined endpoints
export const { useScrapeBooksByBookListIdMutation } = bookApi;
export const { useGetBooksByGenreIdQuery } = bookApi;
