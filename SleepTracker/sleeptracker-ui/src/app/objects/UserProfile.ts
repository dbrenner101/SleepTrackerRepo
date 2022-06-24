import { Account } from "./Account";

export class UserProfile {

    constructor(
        public account: Account,
        public targetSleepHours: number,
        public birthdate?: Date,
        public gender?: string,
        public weight?: number,
        public firstName?: string,
        public lastName?: string,
        public userProfileId?: number
    ) {}
}
