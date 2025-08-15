import { ArticleComment } from "./ArticleComment.interface";

export interface postDetail {
 id: number;
  title: string;
  content: string;
  authorName: string;
  topicName: string;
  createdAt: string;
  comments: ArticleComment [];
}