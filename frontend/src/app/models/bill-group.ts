import { User } from './user';
export class BillGroup {
  id: string;
  groupName: string;
  groupOwner: User;
  groupParticipants: Array<User>;
  createdAt: string;
}
