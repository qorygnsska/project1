package com.ss.batch.job.pass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.ss.batch.entity.BulkPassEntity;
import com.ss.batch.entity.BulkPassStatus;
import com.ss.batch.entity.PassEntity;
import com.ss.batch.entity.UserGroupMappingEntity;
import com.ss.batch.modelmapper.PassModelMapper;
import com.ss.batch.repository.BulkPassRepository;
import com.ss.batch.repository.PassRepository;
import com.ss.batch.repository.UserGroupMappingRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AddPassesTasklet implements Tasklet {

    private final PassRepository passRepository;
    private final BulkPassRepository bulkPassRepository;
    private final UserGroupMappingRepository groupRepo;

    public AddPassesTasklet(PassRepository passRepository, BulkPassRepository bulkPassRepository,
            UserGroupMappingRepository groupRepo) {

        this.passRepository = passRepository;
        this.bulkPassRepository = bulkPassRepository;
        this.groupRepo = groupRepo;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        final LocalDateTime startAt = LocalDateTime.now().minusDays(1);

        List<BulkPassEntity> bulkPassEntities = bulkPassRepository
                .findByStatusAndStartedAtGreaterThan(BulkPassStatus.READY, startAt);

        int count = 0;

        for (BulkPassEntity bulkPassEntity : bulkPassEntities) {
            List<String> userIds = groupRepo.findByUserGroupId(bulkPassEntity.getUserGroupId()).stream()
                    .map(UserGroupMappingEntity::getUserId).collect(Collectors.toList());

            count += addPass(userIds, bulkPassEntity);
            bulkPassEntity.setStatus(BulkPassStatus.COMPLETED);
        }

        log.info("tasklet: execute 이용권:{}건 추가완료 startedAt: {}", count, startAt);

        return RepeatStatus.FINISHED;
    }

    public int addPass(List<String> userIds, BulkPassEntity bulkPassEntity) {

    	
    	
    	  List<PassEntity> passEntities = new ArrayList<>();
          for (String userId : userIds) {
              PassEntity passEntity = PassModelMapper.toPassEntity(userId,bulkPassEntity);
              passEntities.add(passEntity);

          }
          return passRepository.saveAll(passEntities).size();

    }
}


