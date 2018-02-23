import { BaseEntity } from './../../shared';

export const enum ReqTypeEnum {
    'RICORSOTRIBUTARIO',
    'RATEAZIONE'
}

export class RequestType implements BaseEntity {
    constructor(
        public id?: number,
        public reqType?: ReqTypeEnum,
        public code?: string,
        public requests?: BaseEntity[],
    ) {
    }
}
