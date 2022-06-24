import { UserProfile } from "./UserProfile";

export class Account {

    constructor(
        public username: string,
        public password: string,
        public accountId?: number,
        public userProfile?: UserProfile
    ) {}
}
