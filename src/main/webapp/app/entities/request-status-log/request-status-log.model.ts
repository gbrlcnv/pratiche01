import { BaseEntity } from './../../shared';

export class RequestStatusLog implements BaseEntity {
    constructor(
        public id?: number,
        public note?: string,
        public statusFromDate?: any,
        public statusChangeDate?: any,
        public campaign?: BaseEntity,
        public oldStatus?: BaseEntity,
        public newStatus?: BaseEntity,
    ) {
    }
}
