import { BaseEntity } from './../../shared';

export const enum RequestStatusEnum {
    'PROPOSED',
    'OPEN',
    'CLOSED',
    'REFUSED',
    'APPROVED',
    'DELETED',
    'SUCCESS'
}

export class RequestStatus implements BaseEntity {
    constructor(
        public id?: number,
        public code?: RequestStatusEnum,
        public description?: string,
        public requests?: BaseEntity[],
        public requestStatusLogs?: BaseEntity[],
    ) {
    }
}
