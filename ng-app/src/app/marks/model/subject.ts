import {Mark} from '../marks.component';

export interface Subject {
  id: string;
  name: string;
  marks: Mark[];
  nbOfMarks: number;
}
