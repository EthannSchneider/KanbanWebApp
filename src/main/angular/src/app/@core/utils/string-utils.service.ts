import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StringUtilsService {
    constructor(
    ) { }

    static reduceString(string: string, length: number): string {
        if (string.length > length) {
            return string.substring(0, length) + '...';
        }
        return string;
    }
}
