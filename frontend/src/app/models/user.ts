export class User {
  id: number;
  username: string;
  password: string;
  email: string;
  phone: string;

  constructor(username: string, password: string, email: string, phone: string) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.phone = phone;
  }
}
