import {Operator} from 'rxjs/Operator';
import {Observable} from 'rxjs/Observable';
import {Subscriber} from 'rxjs/Subscriber';
import {OperatorFunction} from 'rxjs/interfaces';
import {ClassType} from "class-transformer/ClassTransformer";
import {plainToClass} from "class-transformer";

export function plainToClassOp<T, R>(cls: ClassType<R>): OperatorFunction<T, R> {
    return function mapOperation(source: Observable<T>): Observable<R> {
        return source.lift(new PlainToClassOperator(cls));
    };
}

export function plainToClassArrayOp<T extends Array<any>, R>(cls: ClassType<R>): OperatorFunction<T, R[]> {
    return function mapOperation(source: Observable<T>): Observable<R[]> {
        return source.lift(new PlainToClassOperator(cls));
    };
}


export class PlainToClassOperator<T, R> implements Operator<T, R> {
    constructor(private cls: ClassType<R>) {
    }

    call(subscriber: Subscriber<R>, source: any): any {
        return source.subscribe(new PlainToClassSubscriber(subscriber, this.cls));
    }
}

class PlainToClassSubscriber<T, R> extends Subscriber<T> {
    constructor(destination: Subscriber<R>, private cls: ClassType<R>) {
        super(destination);
    }

    protected _next(value: T) {
        let result: any;
        try {
            result = plainToClass(this.cls, value);
            console.log(result);
        } catch (err) {
            this.destination.error(err);
            return;
        }
        this.destination.next(result);
    }
}