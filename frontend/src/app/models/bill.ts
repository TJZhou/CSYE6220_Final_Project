import { BillGroup } from './bill-group';
import { User } from './user';

export class Bill {
  id: number;
  userContributor: User;
  userParticipants: Array<User>;
  createdAt: string;
  note: string;
  type: string;
  amount: number;
  billGroup: BillGroup;
}
