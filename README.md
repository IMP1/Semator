# Semator

For my 2nd year of my Computer Science Degree I was asked to implement semaphores, in Java, using a Java monitor.

I was then asked to make a monitor, using semaphores made of monitors (although, in theory, I could use an existing semaphore class and it would work the same). I was asked to test with two producers, and two consumers trying to read and write, respectively, from and to a bounded buffer. 

I hope this work can help someone in the same position, or someone wanting to understand semaphores and/or monitors a bit better.

The classes are all fairly self-contained, and hopefully clear, but I've written some notes about them below.

## Groundwork
* The [Bounded Integer Buffer](https://github.com/IMP1/Semator/blob/master/Concurrency/src/BoundedIntBuffer.java).
	* This can be of any size, but its size is static.
	* This more-or-less implements a circular array and has a `readHead` and a `writeHead` to keep track of where to be reading and writing from, respectively.
* The [Producer](https://github.com/IMP1/Semator/blob/master/Concurrency/src/Producer.java).
	* This just writes to a bounded buffer. You can set an amount of times for it to do it, or have it looping forever.
* The [Consumer](https://github.com/IMP1/Semator/blob/master/Concurrency/src/Consumer.java).
	* This just reads from a bounded buffer. You can set an amount of times for it to do it, or have it looping forever.

## Semaphores out of Monitors
* The [General, Counting, Semaphore](https://github.com/IMP1/Semator/blob/master/Concurrency/src/GeneralSemaphore.java).
	* Has a maximum number of permits it can give out, and an initial value of how many it has to begin with.
	* A thread may take a permit, if there are any left, and continue on about its merry way.
	* If there are no permits left, a thread will wait for one to become available.
* The [Binary Semaphore](https://github.com/IMP1/Semator/blob/master/Concurrency/src/BinarySemaphore.java).
	* Has a maximum of one permit it can give out, and may start with 0 initially available, if you want.
	* Otherwise, same as a general semaphore.
* The [Semaphore-Synchronised Bounded Buffer](https://github.com/IMP1/Semator/blob/master/Concurrency/src/SyncedBoundedIntBuffer.java).
	* This uses two counting semaphores. As one goes down, the other goes up. 
	* This is because the writers are taking from one, and the producers from another and, when something has been written there is one fewer space to write to (decrement the producer counting semaphore), and one more space to write from (increment the consumer counting semaphore).
	* It also uses a binary semaphore to protect access to writing and reading, so only one thread can perform any action on the buffer itself at any time.
* The [Main Class](https://github.com/IMP1/Semator/blob/master/Concurrency/src/Main.java).
	* This simply creates two producer instances, and two consumer instances, passing them an instance of the Semaphore-Synchronised Bounded Buffer, and then starts them.

## Monitors out of Semaphores
* The [Monitor](https://github.com/IMP1/Semator/blob/master/Concurrency/src/Monitor.java).
	* Because `wait`, `notify` and `notifyAll` are final methods in Java, I can't use those names, so I went for `_wait`, `_notify` and `_notifyAll`, which I'm not happy with, but that's what I did.
	* Java Monitors handle their acquisition and release behind the scenes when a thread enters and leaves a synchronised block or method. Because I can't do this, I have explicit `acquire` and `release` methods that are called at the beginning and end of "synchronised" code blocks.
	* The Monitor `_wait` call also calls `release` before making the thread wait (see the the last point of Monitor-Synchronised Bounded Buffer) and has to call `acquire` afterwards to reacquire the lock before continuing within the "synchronised" block.
	* `_notify` and `_notifyAll` are fairly straightforward: they release one (or all) the waiting threads.
* The [Monitor-Synchronised Bounded Buffer](https://github.com/IMP1/Semator/blob/master/Concurrency/src/SyncedBoundedIntBuffer2.java).
	* The monitor usage in this is similar to that in General Semaphore.
	* The monitor is used at the beginning and end of each of the methods, to ensure that only one thread is accessing the 'critical section' of the code.
	* If the thread cannot perform its task (e.g. trying to write to a full buffer) then it makes itself wait. This means all producers trying to write to a full buffer, or all consumers trying to read from an empty buffer, will end up waiting.
	* When a thread starts waiting, it gives up the lock so another thread can enter the 'critical section'. This is because the thread is waiting, and so 
* The [Main Class](https://github.com/IMP1/Semator/blob/master/Concurrency/src/Main2.java).
	* This simply creates two producer instances, and two consumer instances, passing them an instance of the Monitor-Synchronised Bounded Buffer, and then starts them.
