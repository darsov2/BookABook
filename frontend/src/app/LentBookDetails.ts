import {Book} from "./Book";
import {UserAvatar} from "../UserAvatar";

export interface LentBookDetails {
  id: number;
  dateFrom: Date,
  dateTo: Date,
  book: Book,
  customer: UserAvatar
}
