package com.example.springintegrationlockoracle;

import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Service
public class SuperService {

    LockRegistry lockRegistry;

    SpecialService specialService;

    SuperService(LockRegistry lockRegistry, SpecialService specialService) {
        this.lockRegistry = lockRegistry;
        this.specialService = specialService;
    }

    public void power(String foo, String bar) {
        Lock lock = lockRegistry.obtain(foo);
        try {
            if (lock.tryLock(2, TimeUnit.MINUTES)) {
                specialService.special(foo, bar);
            } else {
                throw new RuntimeException("Lock not obtained");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
