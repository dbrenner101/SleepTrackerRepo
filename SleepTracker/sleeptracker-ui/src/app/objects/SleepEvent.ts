import { Attitude } from "./Attitude";
import { Location } from "./Location";
import { Diet } from "./Diet";
import { Habit } from "./Habit";
import { Health } from "./Health";
import { SleepResult } from "./SleepResult";
import { SleepCondition } from "./SleepCondition";
import { Account } from "./Account";

export class SleepEvent {
    constructor(
        public sleepStartTime: Date,
        public wakeTime: Date,
        public sleepLength?: number,
        public location?: Location,
        public attitude?: Attitude,
        public diet?: Diet,
        public health?: Health,
        public sleepCondition?: SleepCondition,
        public habits?: Habit[],
        public sleepResults?: SleepResult[],
        public sleepEventId?: number) {}
}
