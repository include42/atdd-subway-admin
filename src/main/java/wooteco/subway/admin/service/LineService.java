package wooteco.subway.admin.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import wooteco.subway.admin.domain.Line;
import wooteco.subway.admin.domain.LineStation;
import wooteco.subway.admin.domain.Station;
import wooteco.subway.admin.dto.LineStationCreateRequest;
import wooteco.subway.admin.repository.LineRepository;
import wooteco.subway.admin.repository.StationRepository;

@Service
public class LineService {
    private LineRepository lineRepository;
    private StationRepository stationRepository;

    public LineService(LineRepository lineRepository, StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    public Line save(Line line) {
        return lineRepository.save(line);
    }

    public boolean existsByName(String name) {
        return lineRepository.existsByName(name);
    }

    public List<Line> showLines() {
        return lineRepository.findAll();
    }

    public Line showLine(Long id) {
        return lineRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void updateLine(Long id, Line line) {
        Line persistLine = lineRepository.findById(id).orElseThrow(RuntimeException::new);
        persistLine.update(line);
        lineRepository.save(persistLine);
    }

    public void deleteLineById(Long id) {
        lineRepository.deleteById(id);
    }

    public void addLineStation(Long id, LineStationCreateRequest request) {
        // TODO: 구현
    }

    public void removeLineStation(Long lineId, Long stationId) {
        // TODO: 구현
    }
    //
    // public LineResponse findLineWithStationsById(Long id) {
    //     // TODO: 구현
    //     return new LineResponse();
    // }

    public Set<Station> toStations(Set<LineStation> lineStations) {
        Map<Long, Long> idMap = new HashMap<>();
        Iterator iterator = lineStations.iterator();
        while (iterator.hasNext()) {
            LineStation lineStation = (LineStation)iterator.next();
            idMap.put(lineStation.getPreStationId(), lineStation.getStationId());
        }
        Set<Station> stations = new LinkedHashSet<>();
        Long preStationId = 0L;
        while (idMap.containsKey(preStationId)) {
            Long stationId = idMap.get(preStationId);
            Station station = stationRepository.findById(stationId)
                .orElseThrow(RuntimeException::new);
            stations.add(station);
            preStationId = stationId;
        }
        return stations;
    }
}
