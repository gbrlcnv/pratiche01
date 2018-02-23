import { BaseEntity } from './../../shared';

export const enum YesNotEnum {
    'YES',
    'NO'
}

export const enum IDTypeEnum {
    'CARTAIDENTITA',
    'PASSAPORTO'
}

export class Person implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public birthDate?: any,
        public piva?: string,
        public cf?: string,
        public phoneNumber?: string,
        public flagLegal?: YesNotEnum,
        public idType?: IDTypeEnum,
        public idNumber?: string,
        public flagNewsletter?: YesNotEnum,
        public requests?: BaseEntity[],
    ) {
    }
}
