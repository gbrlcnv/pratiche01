import { BaseEntity } from './../../shared';

export const enum InOutEnum {
    'IN',
    'OUT'
}

export class RequestDoc implements BaseEntity {
    constructor(
        public id?: number,
        public submissionDate?: any,
        public updateDate?: any,
        public description?: string,
        public inOut?: InOutEnum,
        public request?: BaseEntity,
        public doc?: BaseEntity,
    ) {
    }
}
