import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

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
  }),
});

// Export hooks for usage in functional components, which are
// auto-generated based on the defined endpoints
export const { useScrapeBooksByBookListIdMutation } = bookApi;
