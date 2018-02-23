import { BaseEntity } from './../../shared';

export const enum LocaleEnum {
    'EN',
    'IT'
}

export class DocStore implements BaseEntity {
    constructor(
        public id?: number,
        public locale?: LocaleEnum,
        public code?: string,
        public title?: string,
        public description?: string,
        public contentBinaryContentType?: string,
        public contentBinary?: any,
        public creationDate?: any,
        public contentTextContentType?: string,
        public contentText?: any,
        public mimeType?: string,
        public path?: string,
    ) {
    }
}
