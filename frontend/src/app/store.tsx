import { configureStore } from "@reduxjs/toolkit";
import { bookApi } from "shared/api/bookApi";
import { bookListApi } from "../shared/api/bookListApi";
import { genreApi } from "../shared/api/genreApi";

export default configureStore({
  reducer: {
    [genreApi.reducerPath]: genreApi.reducer,
    [bookListApi.reducerPath]: bookListApi.reducer,
    [bookApi.reducerPath]: bookApi.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware()
      .concat(genreApi.middleware)
      .concat(bookListApi.middleware)
      .concat(bookApi.middleware),
});
