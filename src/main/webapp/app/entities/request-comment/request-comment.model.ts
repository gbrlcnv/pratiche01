import { BaseEntity } from './../../shared';

export const enum InOutEnum {
    'IN',
    'OUT'
}

export class RequestComment implements BaseEntity {
    constructor(
        public id?: number,
        public text?: string,
        public title?: string,
        public commentDate?: any,
        public inOut?: InOutEnum,
        public request?: BaseEntity,
    ) {
    }
}
